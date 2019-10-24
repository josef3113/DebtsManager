package com.example.debtsmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debtsmanager.R;
import com.example.debtsmanager.interfaces.LongPressReader;
import com.example.debtsmanager.models.Debt;

import java.util.ArrayList;
import java.util.List;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.DebtAdapterHolder>
{

    private List<Debt> list;
    private LongPressReader<Debt> pressReader;


    public DebtAdapter(ArrayList<Debt> list) {

        this.list = list;
    }

    public DebtAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.debt_to_cell, parent, false);
        return new DebtAdapterHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final DebtAdapterHolder holder, int position) {

        final Debt debt = list.get(position);

        holder.debtToCellAmount.setText(debt.getAmount()+"");
        holder.debtToCellFromUser.setText(debt.getFrom());
        holder.debtToCellToPerson.setText(debt.getTo());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                if(pressReader != null)
                {
                    pressReader.onClicked(debt);
                }
                return true;
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setlist(List<Debt> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void setPressReader(LongPressReader<Debt> pressReader) {
        this.pressReader = pressReader;
    }

    public static class DebtAdapterHolder extends RecyclerView.ViewHolder {

        final TextView debtToCellAmount;
        final TextView debtToCellToPerson;
        final TextView debtToCellFromUser;

        DebtAdapterHolder(@NonNull View itemView)
        {
            super(itemView);
            debtToCellAmount = itemView.findViewById(R.id.debtToCellAmount);
            debtToCellToPerson = itemView.findViewById(R.id.debtToCellToPerson);
            debtToCellFromUser = itemView.findViewById(R.id.debtToCellFromUser);
        }


    }
}

