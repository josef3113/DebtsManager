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

public class DebtToOtherAdapter extends RecyclerView.Adapter<DebtToOtherAdapter.DebtToOtherAdapterHolder>
{

    private List<Debt> list;
    private LongPressReader<Debt> pressReader;


    public DebtToOtherAdapter(ArrayList<Debt> list) {

        this.list = list;
    }

    public DebtToOtherAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.debt_to_cell, parent, false);
        DebtToOtherAdapterHolder holder = new DebtToOtherAdapterHolder(listItem);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final DebtToOtherAdapter.DebtToOtherAdapterHolder holder, int position) {

        final Debt debt = list.get(position);

        holder.debtToCellAmount.setText(debt.getAmount()+"");
        holder.debtToCellToPerson.setText(debt.getFrom());
        holder.debtToCellFromUser.setText(debt.getTo());
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

    public static class DebtToOtherAdapterHolder extends RecyclerView.ViewHolder {

        final ImageView personImage;
        final TextView debtToCellAmount;
        final TextView debtToCellToPerson;
        final TextView debtToCellDate;
        final TextView debtToCellFromUser;

        DebtToOtherAdapterHolder(@NonNull View itemView)
        {
            super(itemView);
            personImage = itemView.findViewById(R.id.debtToCellPersonImage);
            debtToCellAmount = itemView.findViewById(R.id.debtToCellAmount);
            debtToCellToPerson = itemView.findViewById(R.id.debtToCellToPerson);
            debtToCellDate = itemView.findViewById(R.id.debtToCellDate);
            debtToCellFromUser = itemView.findViewById(R.id.debtToCellFromUser);
        }


    }
}

